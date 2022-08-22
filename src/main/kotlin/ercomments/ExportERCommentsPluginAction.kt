package ercomments

import com.vp.plugin.ApplicationManager
import com.vp.plugin.ViewManager
import com.vp.plugin.action.VPAction
import com.vp.plugin.action.VPContext
import com.vp.plugin.action.VPContextActionController
import com.vp.plugin.model.IDBTable
import com.vp.plugin.model.factory.IModelElementFactory
import java.awt.Component
import java.awt.event.ActionEvent
import java.io.PrintWriter
import javax.swing.JFileChooser


class ExportERCommentsPluginAction : VPContextActionController {

    private val TABLE_COMMENT = """COMMENT ON TABLE #TABLE_NAME IS '#COMMENT';"""
    private val COLUMN_COMMENT = """COMMENT ON COLUMN #TABLE_NAME.#COLUMN_NAME IS '#COMMENT';"""

    private val viewManager : ViewManager
            get() = ApplicationManager.instance().viewManager

    private fun makeCommentValue(s: String?): String =
        if (s.isNullOrBlank()) {
            "NULL"
        } else {
            s.replace("'", "''")
        }

    private fun makeTableComment(name: String, comment: String): String =
        TABLE_COMMENT
            .replace("#TABLE_NAME", name)
            .replace("#COMMENT", makeCommentValue(comment))

    private fun makeColumnComment(table: String, column: String, comment: String): String =
        COLUMN_COMMENT
            .replace("#TABLE_NAME", table)
            .replace("#COLUMN_NAME", column)
            .replace("#COMMENT", makeCommentValue(comment))

    /**
     * Предлагаем пользователю выбрать файл и если он выбран - вызываем action.
     */
    private fun chooseFile(action: (PrintWriter) -> Unit) {
        val fc = viewManager.createJFileChooser()
        if (fc.showSaveDialog(viewManager.rootFrame) == JFileChooser.APPROVE_OPTION) {
            val file = fc.selectedFile
            viewManager.showMessage("Export ER Comments into ${file.absoluteFile}")
            file.printWriter().use {
                action(it)
                it.flush()
            }
            viewManager.showMessage("export completed")
        }
    }

    override fun performAction(action: VPAction, context: VPContext, event: ActionEvent) {
        chooseFile {writer ->


            val diagram = context.diagram

            val tables = diagram
                // Получаем массив всех элементов диаграммы, которые относятся к таблицам
                .toDiagramElementArray(IModelElementFactory.MODEL_TYPE_DB_TABLE)
                // Достаем из элемента диаграммы элемент модели
                .map {it.modelElement as IDBTable}

            tables.forEach {table ->
                viewManager.showMessage("  export table ${table.name}")
                writer.println(makeTableComment(table.name, table.description))

                table.toDBColumnArray().forEach {column ->
                    writer.println(makeColumnComment(table.name, column.name, column.description))
                }
            }
        }
    }

    override fun update(vpAction: VPAction, vpContext: VPContext) {

    }
}