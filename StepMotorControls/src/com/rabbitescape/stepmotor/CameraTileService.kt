package com.rabbitescape.stepmotor

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import java.io.File

class CameraTileService : TileService() {

    private val orientationPath = "/sys/devices/platform/step_motor_ms35774/orientation"

    override fun onTileAdded() {
        super.onTileAdded()
        updateTile()
    }

    override fun onStartListening() {
        super.onStartListening()
        updateTile()
    }

    override fun onClick() {
        super.onClick()
        cycleState()
    }

    private fun updateTileTo(state: String) {
        val stateLabel = when (state) {
            "0" -> "Front"
            "90" -> "Privacy"
            "180" -> "Rear"
            else -> "Privacy" // Default to Privacy if the value is invalid
        }
        val stateValue = if (stateLabel == "Privacy") Tile.STATE_INACTIVE else Tile.STATE_ACTIVE

        qsTile.label = stateLabel
        qsTile.state = stateValue
        qsTile.updateTile()
    }

    private fun updateTile() {
        updateTileTo("foo")
    }

    private fun cycleState() {
        val currentState = readFromFile(orientationPath)
        val newState = when (currentState) {
            "0" -> "90"
            "90" -> "180"
            "180" -> "0"
            else -> "90" // Default to Privacy if the value is invalid
        }
        writeToFile(orientationPath, newState)
        updateTileTo(newState)
    }

    private fun readFromFile(filePath: String): String {
        return try {
            File(filePath).readText().trim()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("CameraTileService", "Error reading from file", e)
            "90" // Default to Privacy if there's an error
        }
    }

    private fun writeToFile(filePath: String, value: String) {
        try {
            File(filePath).writeText(value)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("CameraTileService", "Error writing to file", e)
        }
    }
}
