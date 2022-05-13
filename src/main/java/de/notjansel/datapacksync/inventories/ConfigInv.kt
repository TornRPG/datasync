package de.notjansel.datapacksync.inventories

import de.notjansel.datapacksync.Datapacksync
import de.notjansel.datapacksync.enums.VersionTypes
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class ConfigInv : InventoryHolder {



    companion object {
        lateinit var inv: Inventory
        lateinit var upchecktrue: ItemStack
        lateinit var upcheckfalse: ItemStack
        lateinit var releasechannel: ItemStack
        lateinit var releaseCandidateChannel: ItemStack
        lateinit var betaChannel: ItemStack
        fun GetConfigInv() {
            inv = Bukkit.createInventory(null, 9, "DatapackSync GUI Config")
            InvItems()
            init()
        }
        private fun InvItems() {
            upchecktrue = ItemStack(Material.GREEN_CONCRETE)
            var meta1: ItemMeta = upchecktrue.itemMeta
            meta1.displayName(Component.text(ChatColor.GREEN.toString() + "Update Checks enabled"))
            upchecktrue.itemMeta = meta1
            upcheckfalse= ItemStack(Material.RED_CONCRETE)
            var meta2: ItemMeta = upcheckfalse.itemMeta
            meta2.displayName(Component.text(ChatColor.RED.toString() + "Update Checks disabled"))
            upcheckfalse.itemMeta = meta2
            releasechannel = ItemStack(Material.COMMAND_BLOCK)
            var meta3: ItemMeta = releasechannel.itemMeta
            meta3.displayName(Component.text(ChatColor.LIGHT_PURPLE.toString() + "Update Channel:"))
            var meta3list: MutableList<Component> = ArrayList()
            meta3list.add(Component.text("§r§b> Release"))
            meta3list.add(Component.text("§r§c Release Candidate"))
            meta3list.add(Component.text("§r§c Beta"))
            meta3list.add(Component.text(""))
            meta3list.add(Component.text("§7Left-Click to go down, Right-Click to go up"))
            meta3.lore(meta3list)
            releasechannel.itemMeta = meta3
            releaseCandidateChannel = ItemStack(Material.COMMAND_BLOCK)
            var meta4: ItemMeta = releaseCandidateChannel.itemMeta
            meta4.displayName(Component.text(ChatColor.LIGHT_PURPLE.toString() + "Update Channel:"))
            var meta4list: MutableList<Component> = ArrayList()
            meta4list.add(Component.text("§r§c Release"))
            meta4list.add(Component.text("§r§b> Release Candidate"))
            meta4list.add(Component.text("§r§c Beta"))
            meta4list.add(Component.text(""))
            meta4list.add(Component.text("§7Left-Click to go down, Right-Click to go up"))
            meta4.lore(meta4list)
            releaseCandidateChannel.itemMeta = meta4
            betaChannel = ItemStack(Material.COMMAND_BLOCK)
            var meta5: ItemMeta = betaChannel.itemMeta
            meta5.displayName(Component.text(ChatColor.LIGHT_PURPLE.toString() + "Update Channel:"))
            var meta5list: MutableList<Component> = ArrayList()
            meta5list.add(Component.text("§r§c Release"))
            meta5list.add(Component.text("§r§c Release Candidate"))
            meta5list.add(Component.text("§r§b> Beta"))
            meta5list.add(Component.text(""))
            meta5list.add(Component.text("§7Left-Click to go down, Right-Click to go up"))
            meta5.lore(meta5list)
            betaChannel.itemMeta = meta5
        }

        private fun init() {
            if (Datapacksync.configfile.getBoolean("datasync.auto_check")) {
                inv.addItem(upchecktrue)
            } else {
                inv.addItem(upcheckfalse)
            }
            when (Datapacksync.configfile.get("datasync.update_channel")) {
                VersionTypes.BETA.name -> {inv.addItem(betaChannel)}
                VersionTypes.RELEASE_CANDIDATE.name -> {inv.addItem(releaseCandidateChannel)}
                VersionTypes.RELEASE.name -> {inv.addItem(releasechannel)}
            }
        }
    }





    override fun getInventory(): Inventory {
        return inv
    }
}