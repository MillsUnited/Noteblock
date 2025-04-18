package com.mills.noteblock;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class NoteblockManager implements Listener {

    @EventHandler
    public void onClickNoteBlock(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();
        if (block == null || block.getType() != Material.NOTE_BLOCK) return;

        Block above = block.getRelative(0, 1, 0);
        if (above.getType() == Material.AIR) return;

        NoteBlock noteBlock = (NoteBlock) block.getBlockData();
        Instrument instrument = noteBlock.getInstrument();
        Note currentNote = noteBlock.getNote();

        int noteId = currentNote.getId();

        Note noteToPlay;
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            noteToPlay = new Note(noteId);
        } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            int nextId = (noteId + 1) % 25;
            noteToPlay = new Note(nextId);
        } else {
            return;
        }

        block.getWorld().playNote(block.getLocation(), instrument, noteToPlay);
    }
}
