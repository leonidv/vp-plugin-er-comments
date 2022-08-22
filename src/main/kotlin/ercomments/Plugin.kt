package ercomments

import com.vp.plugin.VPPlugin
import com.vp.plugin.VPPluginInfo

class Plugin : VPPlugin {

    override fun loaded(vpPluginInfo: VPPluginInfo) {
        // Выводим сообщение в стандартный вывод. Вы можете увидеть сообщение
        // в файле vp.log, который лежит в каталоге настроек пользователя VP.
        // В этом же каталоге находится папка plugins.
        println("plugin [ercomments] is loaded ")
   }

    override fun unloaded() {
        println("[ercomments] is unloaded")
    }
}