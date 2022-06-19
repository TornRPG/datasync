package de.notjansel.datapacksync.listeners

import de.notjansel.datapacksync.Datapacksync
import de.notjansel.datapacksync.enums.VersionTypes
import de.notjansel.datapacksync.inventories.ConfigInv
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.DragType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import java.io.File

class InventoryListener: Listener {
    @EventHandler
    fun onItemMove(event: InventoryClickEvent) {
        if (event.clickedInventory == null) {
            return;
        }
        if (event.clickedInventory == ConfigInv.inv && (event.isShiftClick || event.whoClicked.openInventory == ConfigInv.inv)) {
            when (event.currentItem) {
                ConfigInv.upchecktrue -> {
                    ConfigInv.inv.setItem(event.slot, ConfigInv.upcheckfalse)
                    Datapacksync.configfile.set("datasync.auto_check", false)
                }
                ConfigInv.upcheckfalse -> {
                    ConfigInv.inv.setItem(event.slot, ConfigInv.upchecktrue)
                    Datapacksync.configfile.set("datasync.auto_check", true)
                }
                ConfigInv.releasechannel -> {
                    if (event.isLeftClick) {
                        ConfigInv.inv.setItem(event.slot, ConfigInv.releaseCandidateChannel)
                        Datapacksync.configfile.set("datasync.update_channel", VersionTypes.RELEASE_CANDIDATE.name)
                    }
                    if (event.isRightClick) {
                        ConfigInv.inv.setItem(event.slot, ConfigInv.betaChannel)
                        Datapacksync.configfile.set("datasync.update_channel", VersionTypes.BETA.name)
                    }
                }
                ConfigInv.releaseCandidateChannel -> {
                    if (event.isLeftClick) {
                        ConfigInv.inv.setItem(event.slot, ConfigInv.betaChannel)
                        Datapacksync.configfile.set("datasync.update_channel", VersionTypes.BETA.name)
                    }
                    if (event.isRightClick) {
                        ConfigInv.inv.setItem(event.slot, ConfigInv.releasechannel)
                        Datapacksync.configfile.set("datasync.update_channel", VersionTypes.RELEASE.name)
                    }
                }
                ConfigInv.betaChannel -> {
                    if (event.isLeftClick) {
                        ConfigInv.inv.setItem(event.slot, ConfigInv.releasechannel)
                        Datapacksync.configfile.set("datasync.update_channel", VersionTypes.RELEASE.name)
                    }
                    if (event.isRightClick) {
                        ConfigInv.inv.setItem(event.slot, ConfigInv.releaseCandidateChannel)
                        Datapacksync.configfile.set("datasync.update_channel", VersionTypes.RELEASE_CANDIDATE.name)
                    }
                }
            }
            Datapacksync.configfile.save(File(Datapacksync.serverpath + "/plugins/Datapacksync/config.yml"))
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onItemDrag(event: InventoryDragEvent) {
        if (event.inventory == ConfigInv.inv) {
            if (event.type == DragType.EVEN || event.type == DragType.SINGLE) {
                event.isCancelled = true
            }
        }

    }
}