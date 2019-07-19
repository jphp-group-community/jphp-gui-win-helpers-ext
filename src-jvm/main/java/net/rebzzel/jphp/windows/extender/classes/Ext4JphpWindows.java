package net.rebzzel.jphp.windows.extender.classes;

import com.sun.javafx.PlatformUtil;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.IntByReference;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.rebzzel.jphp.windows.extender.Ext4JphpWindowsExtension;
import php.runtime.annotation.Reflection;
import php.runtime.env.Environment;
import php.runtime.lang.BaseObject;
import php.runtime.reflection.ClassEntity;

/**
 * @author Rebzzel
 */
 
@Reflection.Name("Ext4JphpWindows")
@Reflection.Namespace(Ext4JphpWindowsExtension.NAMESPACE)
public class Ext4JphpWindows extends BaseObject {
    public final NativeLibrary user32 = NativeLibrary.getInstance("user32");
    public final NativeLibrary dwmapi = NativeLibrary.getInstance("dwmapi");

    public Ext4JphpWindows(Environment env, ClassEntity clazz) {
        super(env, clazz);
    }

    @Reflection.Signature
    public void addBlur(Object window) {
        Stage stage = (Stage) window;

        if (PlatformUtil.isWinVistaOrLater()) {
            String os = System.getProperty("os.name");
            WinDef.HWND hWnd = new WinDef.HWND(new Pointer(Helper.getWindowPointer(stage)));

            if (os.equalsIgnoreCase("windows vista") || os.equalsIgnoreCase("windows 7")) {
                Structures.DWM_BLURBEHIND dwmbb = new Structures.DWM_BLURBEHIND();
                {
                    dwmbb.dwFlags = 0x00000001;  // DWM_BB_ENABLE
                    dwmbb.fEnable = true;
                    dwmbb.fTransitionOnMaximized = false;
                }

                dwmapi.getFunction("DwmEnableBlurBehindWindow").invoke(new Object[] { hWnd, dwmbb });
            } else {
                Structures.ACCENTPOLICY accentPolicy = new Structures.ACCENTPOLICY();
                {
                    accentPolicy.nAccentState = Enums.AccentState.ACCENT_ENABLE_BLURBEHIND.getValue();
                    accentPolicy.nFlags = 0;
                    accentPolicy.nColor = 0;
                    accentPolicy.nAnimationId = 0;
                }

                int accentPolicySize = accentPolicy.size();

                accentPolicy.write();

                Structures.WINCOMPATTRDATA data = new Structures.WINCOMPATTRDATA();
                {
                    data.nAttribute = Enums.WindowComposionAttribute.WCA_ACCENT_POLICY.getValue();
                    data.pData = accentPolicy.getPointer();
                    data.ulDataSize = accentPolicySize;
                }

                user32.getFunction("SetWindowCompositionAttribute").invoke(new Object[] { hWnd, data });
            }
        } else {
            throw new UnsupportedOperationException(System.getProperty("os.name") + " is not supported!");
        }
    }

    @Reflection.Signature
    public void addBorder(Object window, double thickness, String color)
    {
        Stage stage = (Stage) window;

        StackPane pane = new StackPane();
        pane.setBorder(new Border(new BorderStroke(Color.web(color), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(thickness))));

        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);

        Helper.getRootChildren(stage.getScene().getRoot()).add(pane);
        pane.toBack();

        ResizeHelper.addResizeListener(stage, thickness);
    }

    @Reflection.Signature
    public void addShadow(Object window) {
        // FIXME: Don't working with transparent window style.  
        Stage stage = (Stage) window;

        if (PlatformUtil.isWinVistaOrLater()) {
            WinDef.HWND hWnd = new WinDef.HWND(new Pointer(Helper.getWindowPointer(stage)));
            dwmapi.getFunction("DwmSetWindowAttribute").invoke(new Object[] { hWnd, 2, new IntByReference(2), 4 });

            Structures.MARGINS shadow_on = new Structures.MARGINS();
            {
                shadow_on.cxLeftWidth = 1;
                shadow_on.cxRightWidth = 1;
                shadow_on.cyTopHeight = 1;
                shadow_on.cyBottomHeight = 1;
            }

            dwmapi.getFunction("DwmExtendFrameIntoClientArea").invoke(new Object[] { hWnd, shadow_on });
        } else {
            throw new UnsupportedOperationException(System.getProperty("os.name") + " is not supported!");
        }
    }
}
