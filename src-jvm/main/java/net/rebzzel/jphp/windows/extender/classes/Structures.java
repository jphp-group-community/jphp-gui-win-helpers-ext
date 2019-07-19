package net.rebzzel.jphp.windows.extender.classes;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.IntByReference;

import java.util.Arrays;
import java.util.List;

/**
 * @author Rebzzel
 */
 
public interface Structures extends WinDef {
    class DWM_BLURBEHIND extends Structure implements Structure.ByReference {
        public long dwFlags;
        public boolean fEnable;
        public IntByReference hRgnBlur;
        public boolean fTransitionOnMaximized;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("dwFlags", "fEnable", "hRgnBlur", "fTransitionOnMaximized");
        }
    }

    class MARGINS extends Structure implements Structure.ByReference {
        public int cxLeftWidth;
        public int cxRightWidth;
        public int cyTopHeight;
        public int cyBottomHeight;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("cxLeftWidth", "cxRightWidth", "cyTopHeight", "cyBottomHeight");
        }
    }

    class ACCENTPOLICY extends Structure implements Structure.ByReference {
        public int nAccentState;
        public int nFlags;
        public int nColor;
        public int nAnimationId;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("nAccentState", "nFlags", "nColor", "nAnimationId");
        }
    }

    class WINCOMPATTRDATA extends Structure implements Structure.ByReference {
        public int nAttribute;
        public Pointer pData;
        public int ulDataSize;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("nAttribute", "pData", "ulDataSize");
        }
    }
}
