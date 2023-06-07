package org.example.common.data;

import java.io.Serializable;

/**
 * Enum с видами топлива
 */
public enum FuelType implements Serializable {
    KEROSENE,
    ELECTRICITY,
    PLASMA,
    NUCLEAR,
    ANTIMATTER
}