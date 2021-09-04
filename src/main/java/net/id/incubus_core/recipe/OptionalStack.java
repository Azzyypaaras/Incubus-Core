package net.id.incubus_core.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.tag.ServerTagManagerHolder;
import net.minecraft.tag.SetTag;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class OptionalStack {

    public static final OptionalStack EMPTY = new OptionalStack(SetTag.empty(), 0);

    @NotNull
    private final Tag<Item> tag;
    @NotNull
    private final ItemStack stack;
    private final int count;


    private List<ItemStack> cachedStacks = null;

    public OptionalStack(@NotNull Tag<Item> tag, int count) {
        this.tag = tag;
        this.stack = ItemStack.EMPTY;
        this.count = count;
    }

    public OptionalStack(@NotNull ItemStack stack, int count) {
        this.stack = stack;
        this.tag = Tag.of(Collections.emptySet());
        this.count = count;
    }

    public OptionalStack(Identifier id, int count) {
        this(ServerTagManagerHolder.getTagManager().getOrCreateTagGroup(DefaultedRegistry.ITEM_KEY).getTagOrEmpty(id), count);
    }

    public void write(PacketByteBuf buf) {
        getStacks();
        buf.writeInt(cachedStacks.size());
        for (ItemStack cachedStack : cachedStacks) {
            buf.writeItemStack(cachedStack);
        }
        buf.writeInt(count);
    }

    public static OptionalStack fromByteBuf(PacketByteBuf buf) {
        List<ItemStack> stacks = new ArrayList<>();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            stacks.add(buf.readItemStack());
        }
        OptionalStack folly = new OptionalStack(ItemStack.EMPTY, buf.readInt());
        folly.cachedStacks = stacks;
        return folly;
    }

    public static List<OptionalStack> decodeByteBuf(PacketByteBuf buf, int size) {
        List<OptionalStack> stacks = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            stacks.add(fromByteBuf(buf));
        }
        return stacks;
    }

    public boolean isEmpty() {
        return this == EMPTY || (tag.values().isEmpty() && stack.isEmpty() && cachedStacks.isEmpty());
    }

    public List<ItemStack> getStacks() {
        assert !isEmpty() : "Can't access an empty OptionalStack! Did you check if it was empty first?";
        if(cachedStacks == null) {
            if(tag.values().isEmpty()) {
                cachedStacks = Collections.singletonList(stack);
            }
            else {
                cachedStacks = tag.values().stream().map(item -> new ItemStack(item, count)).collect(Collectors.toList());
            }
        }
        return cachedStacks;
    }

    public int getCount() {
        return count;
    }

    public @Nullable ItemStack getFirstStack() {
        if(cachedStacks == null)
            return getStacks().get(0);
        else
            return cachedStacks.get(0);
    }

    public boolean itemMatch(ItemStack stack) {
        if(cachedStacks == null)
            getStacks();
        if(cachedStacks.isEmpty()) {
            return false;
        }
        else
            return cachedStacks.stream().anyMatch(testStack -> testStack.isItemEqual(stack));
    }

    public boolean contains(ItemStack stack) {
        if(cachedStacks == null)
            getStacks();
        if(cachedStacks.isEmpty()) {
            return false;
        }
        else
            return cachedStacks.stream().anyMatch(testStack -> testStack.isItemEqual(stack) && stack.getCount() >= testStack.getCount());
    }
}
