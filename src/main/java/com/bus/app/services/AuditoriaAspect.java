package com.bus.app.services;

import com.bus.app.controller.AuthenticationController;
import com.bus.app.modelo.RegistroActividad;
import com.bus.app.modelo.UserLogin;
import com.bus.app.repositorio.UsuariosRepositorio;
import com.bus.app.security.BusAppUtils;
import com.bus.app.tools.AuditableEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.lang.reflect.Method;
import java.util.Date;

import static com.bus.app.constantes.Constantes.TIPOS_AUDIT;


@Aspect
@Component
public class AuditoriaAspect {

    @Autowired
    private AuditoriaService auditoriaService;
    @Autowired
    private UsuariosRepositorio usuariosRepositorio;

    @Autowired
    private UserDetailsService UserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Around("@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public Object auditTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

        // Obtener el nombre del método o clase que se está ejecutando
        String metodo = joinPoint.getSignature().getName();
        String clase = joinPoint.getTarget().getClass().getSimpleName();

        // Obtener el método ejecutado
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();
        String httpMethod = "UNKNOWN";

        String tipoEntidad = "Entidad desconocida";
        String objetoAfectado = "Desconocido";

        // Evitar auditar el login
        if (method.getDeclaringClass().equals(AuthenticationController.class) && method.getName().equals("authenticate")) {
            return joinPoint.proceed();
        }

//         Registrar la auditoría antes de la ejecución
//        auditoriaService.registrarAuditoria(usuario, metodo, "Se realizó una modificación en " + clase);

//         Ejecutar el método real
        Object result = joinPoint.proceed();

        // Si el resultado es un ResponseEntity, verificamos su contenido
        if (result instanceof ResponseEntity<?> responseEntity) {
            Object body = responseEntity.getBody();
            if (body instanceof AuditableEntity) {
                tipoEntidad = body.getClass().getSimpleName().replace("DTO", "");
                objetoAfectado = convertirAJson(body);
            } else {
                tipoEntidad = "Entidad desconocida";
                objetoAfectado = "No relevante";
            }
        }
// Si el resultado no es ResponseEntity, verificamos su tipo directamente
        else if (result instanceof AuditableEntity) {
            tipoEntidad = result.getClass().getSimpleName().replace("DTO", "");
            objetoAfectado = convertirAJson(result);
        }
// Si el método recibe un argumento relevante
        else if (args.length > 0 && args[0] instanceof AuditableEntity) {
            tipoEntidad = args[0].getClass().getSimpleName().replace("DTO", "");
            objetoAfectado = convertirAJson(args[0]);
        }










        // Si detectamos un DTO, lo convertimos en su nombre de entidad real
        tipoEntidad = tipoEntidad.replace("DTO", "");

        /*Realiza la auditoria luego de realizar la transacción*/
        String user = BusAppUtils.getUserName();

        UserLogin userObject  = usuariosRepositorio.findByUsu(user);
        RegistroActividad registroActividad = new RegistroActividad();

        registroActividad.setRol(userObject.getRol());
        registroActividad.setUserName(userObject.getUsu());
        String comentario = "";

        // Determinar el tipo de petición inspeccionando anotaciones
        if (method.isAnnotationPresent(PostMapping.class)) {
            httpMethod = "POST";
            comentario = "SE HA GUARDADO UN/A: ".concat(!tipoEntidad.isBlank() ? tipoEntidad : "NO DATA");
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            httpMethod = "PUT";
            comentario = "SE HA EDITADO UN/A: ".concat(!tipoEntidad.isBlank() ? tipoEntidad : "NO DATA");
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            httpMethod = "DELETE";
            comentario = "SE HA BORRADO UN/A: ".concat(!tipoEntidad.isBlank() ? tipoEntidad : "NO DATA");
        }

        registroActividad.setComentarios(comentario);
        registroActividad.setTipoActividad(TIPOS_AUDIT.get(httpMethod.toUpperCase()));
        registroActividad.setFecha(new Date());
        auditoriaService.saveRegistro(registroActividad);

        return result;
    }

    /**
     * Convierte un objeto a JSON de manera segura.
     */
    private String convertirAJson(Object objeto) {
        try {
            return objectMapper.writeValueAsString(objeto);
        } catch (Exception e) {
            return objeto.toString();
        }
    }
}
