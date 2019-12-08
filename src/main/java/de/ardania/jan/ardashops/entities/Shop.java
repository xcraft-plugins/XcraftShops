package de.ardania.jan.ardashops.entities;

import lombok.*;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shop {
    private int shopID;
    private UUID ownerUUID;
    private Location location;
    private List<Item> items;
}
