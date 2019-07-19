package net.rebzzel.jphp.windows.extender;

import net.rebzzel.jphp.windows.extender.classes.Ext4JphpWindows;
import php.runtime.env.CompileScope;
import php.runtime.ext.support.Extension;

/**
 * @author Rebzzel
 */

public class Ext4JphpWindowsExtension extends Extension {
    public static final String NAMESPACE = "gui";

    @Override
    public Status getStatus() {
        return Status.EXPERIMENTAL;
    }

    @Override
    public void onRegister(CompileScope compileScope) {
        registerClass(compileScope, Ext4JphpWindows.class);
    }
}
