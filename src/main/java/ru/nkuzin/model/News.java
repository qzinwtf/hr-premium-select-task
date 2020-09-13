package ru.nkuzin.model;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Builder
public class News implements Serializable {
    private static final long serialVersionUID = 2222L;

    private int priority;
    private String headline;
}
