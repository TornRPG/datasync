package de.notjansel.datapacksync.inventories

import de.notjansel.datapacksync.Datapacksync
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

class configinv : InventoryHolder {
    lateinit var inv: Inventory
    lateinit var upchecktrue: ItemStack
    lateinit var upcheckfalse: ItemStack

    fun GetConfigInv() {
        inv = Bukkit.createInventory(this, 9, "DatapackSync GUI Config")
        InvItems()
        init()
    }

    private fun InvItems() {
        upchecktrue = ItemStack(Material.GREEN_CONCRETE)
        upchecktrue.itemMeta.setDisplayName("Update Checks enabled")
        upcheckfalse= ItemStack(Material.RED_CONCRETE)
        upcheckfalse.itemMeta.setDisplayName("Update Checks disabled")
    }

    private fun init() {
        if (Datapacksync.configfile.getBoolean("datasync.auto_check")) {
            inv.addItem(upchecktrue)
        } else {
            inv.addItem(upcheckfalse)
        }
    }

    override fun getInventory(): Inventory {
        return inv
    }
}