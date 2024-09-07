package pl.blackwater.rpg.items;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.storage.enums.AdditionalModifiers;

@RequiredArgsConstructor
@Getter
public class SpecialItem {

    private final ItemStack itemStack;

    private final float hasTo;

    private final AdditionalModifiers additionalModifiers;

    private final float min;
    private final float max;
}
