package com.app.contador.DTO;

import com.app.contador.modelo.Carro;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImagenDTO {

    private String imagen;

    private String imagenUrl;

    private String imagenDesc;

}
