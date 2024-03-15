package com.example.escolacursosjava.exception;

public class RecordNotFoundException extends RuntimeException{

    private static final long serialVersion = 1L;

    public RecordNotFoundException(Long id) {
        super("Registro não encontrado com o id: " + id);
    }

}
