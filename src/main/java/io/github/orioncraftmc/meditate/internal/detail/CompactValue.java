package io.github.orioncraftmc.meditate.internal.detail;

//static_assert(std::numeric_limits<float>::is_iec559, "facebook::yoga::detail::CompactValue only works with IEEE754 floats");


import io.github.orioncraftmc.meditate.internal.YGValue;
import io.github.orioncraftmc.meditate.internal.enums.YGUnit;

public class CompactValue //Type originates from: CompactValue.h
{
    public static final float LOWER_BOUND = 1.08420217e-19f;
    public static final float UPPER_BOUND_POINT = 36893485948395847680.0f;
    public static final float UPPER_BOUND_PERCENT = 18446742974197923840.0f;
    private final Payload payload_;
    private boolean undefined;

    public CompactValue() {
        undefined = true;
        this.payload_ = new CompactValue.Payload(Float.NaN, YGUnit.YGUnitUndefined);
    }

    private CompactValue(Payload data) {
        this.payload_ = data;
    }

    public static  CompactValue of(float value, YGUnit Unit) {
        if ((value < LOWER_BOUND && value > -LOWER_BOUND)) {
            return new CompactValue(new Payload(0f, Unit));
        }

        final float upperBound = Unit == YGUnit.YGUnitPercent ? UPPER_BOUND_PERCENT : UPPER_BOUND_POINT;
        if (value > upperBound || value < -upperBound) {
            value = Math.copySign(upperBound, value);
        }

        CompactValue.Payload data = new Payload((value), Unit);
        return new CompactValue(data);
    }

    public static  CompactValue ofMaybe(float value, YGUnit Unit) {
        return Float.isNaN(value) || Float.isInfinite(value) ? ofUndefined() : CompactValue.of(value, Unit);
    }

    public static  CompactValue ofZero() {
        return new CompactValue(new Payload(0f, YGUnit.YGUnitPoint));
    }

    public static  CompactValue ofUndefined() {
        return new CompactValue();
    }

    public static  CompactValue ofAuto() {
        return new CompactValue(new Payload(0f, YGUnit.YGUnitAuto));
    }

    public static  CompactValue createCompactValue(final  YGValue x) {
        CompactValue compactValue;
        switch (x.unit) {
            case YGUnitUndefined:
                compactValue = ofUndefined();
                break;
            case YGUnitAuto:
                compactValue = ofAuto();
                break;
            case YGUnitPoint:
                compactValue = CompactValue.of(x.value, YGUnit.YGUnitPoint);
                break;
            case YGUnitPercent:
                compactValue = CompactValue.of(x.value, YGUnit.YGUnitPercent);
                break;
            default:
                throw new IllegalArgumentException();
        }

        return compactValue;
    }

    public static boolean equalsTo( CompactValue a,  CompactValue b) //Method definition originates from: CompactValue.h
    {
        return a.payload_.unit.equals(b.payload_.unit) && a.payload_.value == b.payload_.value;
    }

    public YGValue convertToYgValue() {
        return new YGValue(payload_.value, payload_.unit);
    }

    public final boolean isUndefined() {
        return undefined || (!isAuto() && !isPoint() && !isPercent() && Float.isNaN(payload_.value));
    }

    private boolean isPercent() {
        return payload_.unit.equals(YGUnit.YGUnitPercent);
    }

    private boolean isPoint() {
        return payload_.unit.equals(YGUnit.YGUnitPoint);
    }

    public final boolean isAuto() {
        return payload_.unit.equals(YGUnit.YGUnitAuto);
    }

    private YGUnit repr() {
        return (payload_.unit);
    }

    private static class Payload //Type originates from: CompactValue.h
    {
        public final float value;
        public final YGUnit unit;

        public Payload(float value, YGUnit unit) {
            this.value = value;
            this.unit = unit;
        }

    }
}
