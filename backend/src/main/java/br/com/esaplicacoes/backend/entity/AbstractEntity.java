package br.com.esaplicacoes.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 29/07/2023
 * @since 1.0.0
 */
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @Getter
    @GenericGenerator(
            name = "sequence_generation_id",
            strategy = "enhanced-sequence",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled-lo")
            }
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "sequence_generation_id"
    )
    @Column(name = "id", unique = true, updatable = false)
    private Long id;

    @Getter @Setter
    @Column(name = "dtCadastro", nullable = false)
    private LocalDateTime dtCadastro;

    @Getter @Setter
    @Column(name = "dtAtualizacao")
    private LocalDateTime dtAtualizacao;

    @Getter @Setter
    @Column(name = "ativo", nullable = false)
    private boolean ativo;


    /**
     *
     */
    @PrePersist
    private void prePersist(){
        this.dtCadastro = LocalDateTime.now();
        this.ativo = true;
    }

    /**
     *
     */
    @PreUpdate
    private void preUpdate(){
        this.dtAtualizacao = LocalDateTime.now();
    }
}
