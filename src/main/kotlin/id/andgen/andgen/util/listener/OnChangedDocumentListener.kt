package id.andgen.andgen.util.listener

import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener


@FunctionalInterface
interface OnChangedDocumentListener : DocumentListener {
    fun onUpdated(event: DocumentEvent?)

    override fun insertUpdate(event: DocumentEvent?) {
        onUpdated(event)
    }

    override fun removeUpdate(event: DocumentEvent?) {
        onUpdated(event)
    }

    override fun changedUpdate(event: DocumentEvent?) {
        onUpdated(event)
    }
}