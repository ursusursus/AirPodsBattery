package sk.ursus.airpodsbattery.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

private class AddPaddingValues(val value1: PaddingValues, val value2: PaddingValues) : PaddingValues {
    override fun calculateBottomPadding(): Dp {
        return value1.calculateBottomPadding() + value2.calculateBottomPadding()
    }

    override fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp {
        return value1.calculateLeftPadding(layoutDirection) + value2.calculateLeftPadding(layoutDirection)
    }

    override fun calculateRightPadding(layoutDirection: LayoutDirection): Dp {
        return value1.calculateRightPadding(layoutDirection) + value2.calculateRightPadding(layoutDirection)
    }

    override fun calculateTopPadding(): Dp {
        return value1.calculateTopPadding() + value2.calculateTopPadding()
    }
}

operator fun PaddingValues.plus(paddingValues: PaddingValues): PaddingValues {
    return AddPaddingValues(this, paddingValues)
}