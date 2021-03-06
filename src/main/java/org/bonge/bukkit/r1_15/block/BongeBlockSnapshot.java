package org.bonge.bukkit.r1_15.block;

import org.bonge.Bonge;
import org.bonge.bukkit.r1_15.block.data.BongeAbstractBlockData;
import org.bonge.bukkit.r1_15.world.BongeLocation;
import org.bonge.bukkit.r1_15.world.BongeWorld;
import org.bonge.util.exception.NotImplementedException;
import org.bonge.wrapper.BongeWrapper;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.util.Direction;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

//THIS IS JUST TOO INCOMPLETE FOR ANY USE
@Deprecated
public class BongeBlockSnapshot extends BongeWrapper<org.spongepowered.api.block.BlockSnapshot> implements Block {

    public BongeBlockSnapshot(BlockSnapshot value) {
        super(value);
    }

    public org.spongepowered.api.world.Location<? extends org.spongepowered.api.world.World> getSpongeLocation(){
        return this.spongeValue.getLocation().get();
    }

    @Override
    @Deprecated
    public byte getData() {
        throw new NotImplementedException("Block.isBlockFacePowered(BlockFace) should only be called from legacy plugins which Bonge does not support");
    }

    @Override
    public @NotNull BlockData getBlockData() {
        try {
            return Bonge.getInstance().convert(BlockData.class, this.spongeValue.getState());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @NotNull
    @Override
    public Block getRelative(int modX, int modY, int modZ) {
        return new BongeBlock(this.getSpongeLocation().add(modX, modY, modZ));
    }

    @NotNull
    @Override
    public Block getRelative(@NotNull BlockFace face) {
        try {
            return new BongeBlock(this.getSpongeLocation().add(Bonge.getInstance().convert(face, Direction.class).asBlockOffset()));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @NotNull
    @Override
    public Block getRelative(@NotNull BlockFace face, int distance) {
        try {
            return new BongeBlock(this.getSpongeLocation().add(Bonge.getInstance().convert(face, Direction.class).asBlockOffset().mul(distance)));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public @NotNull Material getType() {
        try {
            return Bonge.getInstance().convert(Material.class, this.spongeValue.getState().getType());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public byte getLightLevel() {
        return 0;
    }

    @Override
    public byte getLightFromSky() {
        return 0;
    }

    @Override
    public byte getLightFromBlocks() {
        return 0;
    }

    @Override
    public @NotNull World getWorld() {
        return new BongeWorld(this.getSpongeLocation().getWorld());
    }

    @Override
    public int getX() {
        return this.spongeValue.getPosition().getX();
    }

    @Override
    public int getY() {
        return this.spongeValue.getPosition().getY();
    }

    @Override
    public int getZ() {
        return this.spongeValue.getPosition().getZ();
    }

    @Override
    public @NotNull Location getLocation() {
        return new BongeLocation(this.getSpongeLocation());
    }

    @Override
    public Location getLocation(Location loc) {
        return null;
    }

    @Override
    public @NotNull Chunk getChunk() {
        return null;
    }

    @Override
    public void setBlockData(@NotNull BlockData data) {
        this.setBlockData(data, true);
    }

    @Override
    public void setBlockData(@NotNull BlockData data, boolean applyPhysics) {
        this.spongeValue.withState(((BongeAbstractBlockData)data).getSpongeValue());
    }

    @Override
    public void setType(Material type) {
        this.setBlockData(type.createBlockData(), true);
    }

    @Override
    public void setType(Material type, boolean applyPhysics) {
        this.setBlockData(type.createBlockData(), applyPhysics);
    }

    @Override
    public BlockFace getFace(@NotNull Block block) {
        return null;
    }

    @Override
    public @NotNull BlockState getState() {
        return null;
    }

    @Override
    public @NotNull Biome getBiome() {
        return null;
    }

    @Override
    public void setBiome(@NotNull Biome bio) {

    }

    @Override
    public boolean isBlockPowered() {
        return false;
    }

    @Override
    public boolean isBlockIndirectlyPowered() {
        return false;
    }

    @Override
    public boolean isBlockFacePowered(@NotNull BlockFace face) {
        return false;
    }

    @Override
    public boolean isBlockFaceIndirectlyPowered(@NotNull BlockFace face) {
        return false;
    }

    @Override
    public int getBlockPower(@NotNull BlockFace face) {
        return 0;
    }

    @Override
    public int getBlockPower() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isLiquid() {
        return false;
    }

    @Override
    public double getTemperature() {
        return 0;
    }

    @Override
    public double getHumidity() {
        return 0;
    }

    @Override
    public @NotNull PistonMoveReaction getPistonMoveReaction() {
        return null;
    }

    @Override
    public boolean breakNaturally() {
        return false;
    }

    @Override
    public boolean breakNaturally(@NotNull ItemStack tool) {
        return false;
    }

    @Override
    public @NotNull Collection<ItemStack> getDrops() {
        return null;
    }

    @Override
    public @NotNull Collection<ItemStack> getDrops(ItemStack tool) {
        return null;
    }

    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public @Nullable RayTraceResult rayTrace(@NotNull Location start, @NotNull Vector direction, double maxDistance, @NotNull FluidCollisionMode fluidCollisionMode) {
        throw new NotImplementedException("Block.rayTrace(Location, Vector, double, FluidCollisionMode) not been looked at");
    }

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        throw new NotImplementedException("Block.getBoundingBox() not been looked at");

    }

    @Override
    public void setMetadata(@NotNull String metadataKey, @NotNull MetadataValue newMetadataValue) {

    }

    @Override
    public @NotNull List<MetadataValue> getMetadata(@NotNull String metadataKey) {
        return null;
    }

    @Override
    public boolean hasMetadata(@NotNull String metadataKey) {
        return false;
    }

    @Override
    public void removeMetadata(@NotNull String metadataKey, @NotNull Plugin owningPlugin) {

    }
}
