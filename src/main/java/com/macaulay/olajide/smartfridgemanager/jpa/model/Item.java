package com.macaulay.olajide.smartfridgemanager.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    private UUID uuid;

    @ManyToOne
    private Type type;

    private String name;

    private Double fillFactor;
}
