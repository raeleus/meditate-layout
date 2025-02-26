package io.github.orioncraftmc.meditate.internal.interfaces;

import io.github.orioncraftmc.meditate.internal.YGNode;
import io.github.orioncraftmc.meditate.internal.YGSize;
import io.github.orioncraftmc.meditate.internal.enums.YGMeasureMode;

@FunctionalInterface
public interface MeasureWithContextFn {
     YGSize invoke(YGNode UnnamedParameter, float UnnamedParameter2, YGMeasureMode UnnamedParameter3, float UnnamedParameter4, YGMeasureMode UnnamedParameter5, Object UnnamedParameter6);
}
