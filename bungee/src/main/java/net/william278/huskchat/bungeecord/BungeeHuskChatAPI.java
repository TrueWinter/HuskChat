/*
 * This file is part of HuskChat, licensed under the Apache License 2.0.
 *
 *  Copyright (c) William278 <will27528@gmail.com>
 *  Copyright (c) contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package net.william278.huskchat.bungeecord;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.william278.huskchat.HuskChat;
import net.william278.huskchat.HuskChatAPI;
import net.william278.huskchat.bungeecord.player.BungeePlayer;
import net.william278.huskchat.player.Player;
import org.jetbrains.annotations.NotNull;

public class BungeeHuskChatAPI extends HuskChatAPI {
    public BungeeHuskChatAPI(HuskChat plugin) {
        super(plugin);
    }

    @Override
    public Player adaptPlayer(@NotNull Object player) {
        if (!(player instanceof ProxiedPlayer)) {
            throw new IllegalArgumentException("Player object must be a Bungee ProxiedPlayer");
        }

        return BungeePlayer.adapt((ProxiedPlayer) player);
    }
}
