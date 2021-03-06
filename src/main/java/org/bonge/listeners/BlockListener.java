package org.bonge.listeners;

import net.kyori.adventure.text.Component;
import org.array.utils.ArrayUtils;
import org.bonge.Bonge;
import org.bonge.bukkit.r1_15.block.BongeBlock;
import org.bonge.bukkit.r1_15.block.BongeBlockSnapshot;
import org.bonge.bukkit.r1_15.entity.living.human.BongePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.*;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.entity.Piston;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.value.ListValue;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.entity.ChangeSignEvent;
import org.spongepowered.api.event.filter.cause.Root;

import java.io.IOException;
import java.util.List;

public class BlockListener {

    @org.spongepowered.api.event.Listener
    public void onPistonChange(ChangeBlockEvent.Post event){
        Object source = event.getSource();
        if(!(source instanceof Piston)){
            return;
        }
        Piston piston = (Piston)source;
        List<Block> blocks = ArrayUtils.convert(t -> {
            try {
                return Bonge.getInstance().convert(Location.class, t.getFinal().getLocation().get()).getBlock();
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }, event.getTransactions());
        try {
            Block block = Bonge.getInstance().convert(Location.class, piston.getLocation()).getBlock();
            BlockFace face = Bonge.getInstance().convert(BlockFace.class, piston.get(Keys.DIRECTION));
            BlockPistonEvent bEvent = null;
            if (piston.get(Keys.IS_EXTENDED).get()) {
                bEvent = new BlockPistonExtendEvent(block, blocks, face);
            }else{
                bEvent = new BlockPistonRetractEvent(block, blocks, face);
            }
            Bukkit.getPluginManager().callEvent(bEvent);
            event.setCancelled(bEvent.isCancelled());
        }catch (IOException e){

        }
    }

    @org.spongepowered.api.event.Listener
    public void onRedstoneChange(ChangeBlockEvent.Modify event){
        Transaction<BlockSnapshot> tBlock = event.getTransactions().get(0);
        if(tBlock.getOriginal().get(Keys.POWER).isPresent() && tBlock.getFinal().get(Keys.POWER).isPresent()){
            int originalPower = tBlock.getOriginal().get(Keys.POWER).get();
            int finalPower = tBlock.getFinal().get(Keys.POWER).get();
            if(originalPower == finalPower){
                return;
            }
            try {
                BlockRedstoneEvent bEvent = new BlockRedstoneEvent(Bonge.getInstance().convert(Location.class, tBlock.getFinal().getLocation().get()).getBlock(), originalPower, finalPower);
                Bukkit.getPluginManager().callEvent(bEvent);
                if(bEvent.getNewCurrent() != finalPower){
                    tBlock.setCustom(BlockSnapshot.builder().from(tBlock.getFinal()).add(Keys.POWER, bEvent.getNewCurrent()).build());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @org.spongepowered.api.event.Listener
    public void onBlockBreak(ChangeBlockEvent.Break event, @Root org.spongepowered.api.entity.living.player.Player player){
        if(event.getTransactions().size() == 1) {
            BlockSnapshot block = event.getTransactions().get(0).getOriginal();
            Player bPlayer = BongePlayer.getPlayer(player);
            BlockBreakEvent bukkitEvent = new BlockBreakEvent(new BongeBlockSnapshot(block), bPlayer);
            Bukkit.getServer().getPluginManager().callEvent(bukkitEvent);
            event.setCancelled(bukkitEvent.isCancelled());
        }
    }

    @org.spongepowered.api.event.Listener
    public void onSignChange(ChangeSignEvent event, @Root org.spongepowered.api.entity.living.player.Player player){
        BongeBlock block = new BongeBlock(event.getSign().getLocation());
        BongePlayer bPlayer = BongePlayer.getPlayer(player);

        String[] lines = new String[4];
        for(int A = 0; A < lines.length; A++){
            lines[A] = Bonge.getInstance().convert(event.getOriginalText().get(A));
        }
        SignChangeEvent bukkitEvent = new SignChangeEvent(block, bPlayer, lines);
        Bukkit.getServer().getPluginManager().callEvent(bukkitEvent);
        ListValue.Mutable<Component> data = event.getText();
        for(int A = 0; A < 4; A++){
            data.set(A, Bonge.getInstance().convertText(bukkitEvent.getLine(A)));
        }
        event.setCancelled(bukkitEvent.isCancelled());
    }
}
