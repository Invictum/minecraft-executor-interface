/*
 * Copyright 2013 Moritz Hilscher
 *
 * This file is part of MapTools.
 *
 * MapTools is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MapTools is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MapTools.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.invictum.mei;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class MarkerUpdateTask extends BukkitRunnable {
    private MeiPlugin plugin;

    public MarkerUpdateTask(MeiPlugin plugin) {
        this.plugin = plugin;
    }

    public void run() {
        writePlayers(plugin.getServer().getOnlinePlayers());
    }

    public void writePlayers(Collection<? extends Player> collection) {
        JSONArray playersJson = new JSONArray();
        for (Player player : collection) {
            JSONObject json = new JSONObject();

            Location pos = player.getLocation();
            World world = player.getWorld();

            json.put("username", player.getName());
            json.put("x", pos.getX());
            json.put("y", pos.getY());
            json.put("z", pos.getZ());
            json.put("world", world.getName());
            json.put("dimension", world.getEnvironment().toString());
            json.put("health", player.getHealth());
            json.put("saturation", player.getSaturation());
            json.put("food", player.getFoodLevel());
            Location bed = player.getBedSpawnLocation();
            if (bed == null) {
                json.put("bed", null);
            } else {
                JSONArray bedJson = new JSONArray();
                bedJson.add(bed.getBlockX());
                bedJson.add(bed.getBlockY());
                bedJson.add(bed.getBlockZ());
                json.put("bed", bedJson);
            }
            json.put("level", (float) player.getLevel() + player.getExp());
            playersJson.add(json);
        }
        final JSONObject json = new JSONObject();
        json.put("players", playersJson);

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                try {
                    File file = new File(plugin.getConfig().getString("markerFile"));
                    BufferedWriter output = new BufferedWriter(new FileWriter(file));
                    output.write(json.toJSONString());
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
