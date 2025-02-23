/*
 * Copyright 2006-2019 ICEsoft Technologies Canada Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS
 * IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.icepdf.ri.common.views.annotations;


import org.icepdf.core.pobjects.acroform.SignatureFieldDictionary;
import org.icepdf.core.pobjects.acroform.SignatureHandler;
import org.icepdf.core.pobjects.acroform.signature.SignatureValidator;
import org.icepdf.core.pobjects.acroform.signature.exceptions.SignatureIntegrityException;
import org.icepdf.core.pobjects.annotations.SignatureWidgetAnnotation;
import org.icepdf.ri.common.views.AbstractPageViewComponent;
import org.icepdf.ri.common.views.Controller;
import org.icepdf.ri.common.views.DocumentViewController;
import org.icepdf.ri.common.views.annotations.signatures.CertificatePropertiesDialog;
import org.icepdf.ri.common.views.annotations.signatures.SignaturePropertiesDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

/**
 * UI component that represents a Acroform signature widget in the interactive UI.
 * Focus, mouse, validation and form submission is handled by this class.
 *
 * @since 6.1
 */
public class SignatureFieldComponent extends AbstractAnnotationComponent<SignatureWidgetAnnotation> {

    private static final Logger logger =
            Logger.getLogger(SignatureFieldComponent.class.toString());

    protected JPopupMenu contextMenu;
    protected Controller controller;

    public SignatureFieldComponent(SignatureWidgetAnnotation annotation, DocumentViewController documentViewController,
                                   AbstractPageViewComponent pageViewComponent) {
        super(annotation, documentViewController, pageViewComponent);

        controller = documentViewController.getParentController();

        isShowInvisibleBorder = true;
        isResizable = false;
        isMovable = false;

        if (!annotation.allowScreenOrPrintRenderingOrInteraction()) {
            // border state flags.
            isEditable = false;
            isRollover = false;
            isMovable = false;
            isResizable = false;
            isShowInvisibleBorder = false;
        }

        // add context menu for quick access to validating and signature properties.
        contextMenu = new JPopupMenu();
        JMenuItem validationMenu = new JMenuItem(messageBundle.getString(
                "viewer.annotation.signature.menu.showCertificates.label"));
        validationMenu.addActionListener(new CertificatePropertiesActionListener());
        contextMenu.add(validationMenu);
        contextMenu.add(new JPopupMenu.Separator());
        JMenuItem signaturePropertiesMenu = new JMenuItem(messageBundle.getString(
                "viewer.annotation.signature.menu.signatureProperties.label"));
        signaturePropertiesMenu.addActionListener(new signerPropertiesActionListener());
        contextMenu.add(signaturePropertiesMenu);
    }

    public boolean isActive() {
        return false;
    }

    @Override
    public void resetAppearanceShapes() {

    }

    @Override
    public void paintComponent(Graphics g) {

    }

    /**
     * Utility for showing SignaturePropertiesDialog via a double click or the context menu.
     */
    protected void showSignatureWidgetPropertiesDialog() {
        SignatureFieldDictionary fieldDictionary = annotation.getFieldDictionary();
        if (fieldDictionary != null) {
            SignatureValidator signatureValidator = annotation.getSignatureValidator();
            if (signatureValidator != null) {
                try {
                    signatureValidator.validate();
                    new SignaturePropertiesDialog(controller.getViewerFrame(),
                            messageBundle, annotation).setVisible(true);
                } catch (SignatureIntegrityException e1) {
                    logger.fine("Error validating annotation " + annotation.toString());
                }
            }
        }
    }

    /**
     * Shows the CertificatePropertiesDialog.
     */
    class CertificatePropertiesActionListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            // validate the signature and show the summary dialog.
            SignatureFieldDictionary fieldDictionary = annotation.getFieldDictionary();
            if (fieldDictionary != null) {
                SignatureHandler signatureHandler = fieldDictionary.getLibrary().getSignatureHandler();
                SignatureValidator signatureValidator = signatureHandler.validateSignature(fieldDictionary);
                if (signatureValidator != null) {
                    try {
                        signatureValidator.validate();
                        new CertificatePropertiesDialog(controller.getViewerFrame(),
                                messageBundle, signatureValidator.getCertificateChain()).setVisible(true);
                    } catch (SignatureIntegrityException e1) {
                        logger.fine("Error validating annotation " + annotation.toString());
                    }
                }
            }
        }
    }

    /**
     * Opens the SignaturePropertiesDialog from a context menu.
     */
    class signerPropertiesActionListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            showSignatureWidgetPropertiesDialog();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (e.getClickCount() == 2) {
            // show signature details dialog.
            showSignatureWidgetPropertiesDialog();
        }
        // pick up on the context menu display
        else if (e.getButton() == MouseEvent.BUTTON3 || e.getButton() == MouseEvent.BUTTON2) {
            contextMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    /**
     * Gets the associated widget annotation.
     *
     * @return SignatureWidgetAnnotation for the instance annotation object.
     */
    private SignatureWidgetAnnotation getSignatureWidgetAnnotation() {
        SignatureWidgetAnnotation widget = null;
        if (annotation instanceof SignatureWidgetAnnotation) {
            widget = (SignatureWidgetAnnotation) annotation;
        } else {
            // corner case for PDF that aren't well formed
            try {
                widget = new SignatureWidgetAnnotation(annotation);
                widget.init();
                annotation = widget;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.fine("Signature component annotation instance creation was interrupted");
            }
        }
        return widget;
    }

}
