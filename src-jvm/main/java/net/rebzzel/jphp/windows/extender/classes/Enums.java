package net.rebzzel.jphp.windows.extender.classes;


/**
 * @author Rebzzel
 */
 
public interface Enums {
    enum AccentState {
        ACCENT_DISABLE(0),
        ACCENT_ENABLE_GRADIENT(1),
        ACCENT_ENABLE_TRANSPARENTGRADIEN(2),
        ACCENT_ENABLE_BLURBEHIND(3),
        ACCENT_INVALID_STATE(4);

        AccentState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        private final int value;
    }

    enum WindowComposionAttribute {
        WCA_ACCENT_POLICY(19);

        WindowComposionAttribute(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        private final int value;
    }
}
