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

package net.william278.huskchat;

import net.william278.huskchat.api.HuskChatAPI;
import net.william278.huskchat.api.player.Player;
import net.william278.huskchat.message.BroadcastMessage;
import net.william278.huskchat.message.ChatMessage;
import net.william278.huskchat.message.PrivateMessage;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class API implements HuskChatAPI {
    private final HuskChat huskChat;

    public API(HuskChat huskChat) {
        this.huskChat = huskChat;
    }

    @Override
    public void getPlayerChannel(@NotNull Player player) {
        huskChat.getPlayerCache().getPlayerChannel(player.getUuid());
    }

    @Override
    public void setPlayerChannel(@NotNull Player player, @NotNull String channel) {
        huskChat.getPlayerCache().setPlayerChannel(player.getUuid(), channel);
    }

    @Override
    public void sendChatMessage(@NotNull String targetChannelId, @NotNull Player sender, @NotNull String message) {
        new ChatMessage(targetChannelId, sender, message, huskChat).dispatch();
    }

    @Override
    public void sendBroadcastMessage(@NotNull Player sender, @NotNull String message) {
        new BroadcastMessage(sender, message, huskChat).dispatch();
    }

    @Override
    public void sendPrivateMessage(@NotNull Player sender, @NotNull List<String> targetUsernames, @NotNull String message) {
        new PrivateMessage(sender, targetUsernames, message, huskChat).dispatch();
    }
}
