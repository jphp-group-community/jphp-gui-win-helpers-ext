package net.rebzzel.jphp.windows.extender.classes;

import com.sun.javafx.tk.TKStage;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.lang.reflect.Method;

public class Helper {
    public static Long getWindowPointer(Stage stage) {
        try {
            Method tkStageGetter;
            try {
                tkStageGetter = stage.getClass().getSuperclass().getDeclaredMethod("getPeer");
            } catch (NoSuchMethodException ex) {
                tkStageGetter = stage.getClass().getMethod("impl_getPeer");
            }
            tkStageGetter.setAccessible(true);
            TKStage tkStage = (TKStage) tkStageGetter.invoke(stage);
            Method getPlatformWindow = tkStage.getClass().getDeclaredMethod("getPlatformWindow");
            getPlatformWindow.setAccessible(true);
            Object platformWindow = getPlatformWindow.invoke(tkStage);
            Method getNativeHandle = platformWindow.getClass().getMethod("getNativeHandle");
            getNativeHandle.setAccessible(true);
            Object nativeHandle = getNativeHandle.invoke(platformWindow);
            return (long) nativeHandle;
        } catch (Throwable e) {
            throw new IllegalStateException("Couldn't get pointer of window");
        }
    }

    public static ObservableList<Node> getRootChildren(Parent parent) {
        try {
            Method getChildrenGetter;
            try {
                getChildrenGetter = parent.getClass().getSuperclass().getMethod("getChildren");
            } catch (NoSuchMethodException ex) {
                getChildrenGetter = parent.getClass().getMethod("getChildren");
            }
            getChildrenGetter.setAccessible(true);
            ObservableList<Node> getChildren = (ObservableList<Node>) getChildrenGetter.invoke(parent);
            return getChildren;
        } catch (Throwable e) {
            throw new IllegalStateException("Couldn't get children of root");
        }
    }

}
