package net.spicapvp.core.clan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.spicapvp.core.util.Style;

@AllArgsConstructor
@Getter
public enum ClanPlayerRole {

    Owner(Style.DARK_RED, 2),
    Leader(Style.PINK, 1),
    Regular(Style.GREEN, 0);


    private String color;
    private int weight;

}
