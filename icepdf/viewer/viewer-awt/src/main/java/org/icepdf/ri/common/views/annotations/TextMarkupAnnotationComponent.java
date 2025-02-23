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

import org.icepdf.core.pobjects.annotations.TextMarkupAnnotation;
import org.icepdf.ri.common.views.AbstractPageViewComponent;
import org.icepdf.ri.common.views.DocumentViewController;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.logging.Logger;

/**
 *
 */
@SuppressWarnings("serial")
public class TextMarkupAnnotationComponent extends MarkupAnnotationComponent<TextMarkupAnnotation> {

    private static final Logger logger =
            Logger.getLogger(TextMarkupAnnotationComponent.class.toString());

    public TextMarkupAnnotationComponent(TextMarkupAnnotation annotation, DocumentViewController documentViewController,
                                         AbstractPageViewComponent pageViewComponent) {
        super(annotation, documentViewController, pageViewComponent);
        isMovable = false;
        isResizable = false;
        isShowInvisibleBorder = false;
    }

    @Override
    public void resetAppearanceShapes() {
        super.resetAppearanceShapes();
        annotation.resetAppearanceStream(getToPageSpaceTransform());
    }

    @Override
    public boolean contains(int x, int y) {
        if (super.contains(x, y) &&
                annotation != null && annotation.getMarkupPath() != null) {
            // convert the mouse coords to component space.
            AffineTransform pageTransform = getPageSpaceTransform();
            Rectangle compBounds = getBounds();
            shape = annotation.getMarkupPath().createTransformedShape(pageTransform);
            AffineTransform af = new AffineTransform(1, 0, 0, 1, -compBounds.x, -compBounds.y);
            shape = af.createTransformedShape(shape);
            // we do the contains test on the annotations shape.
            return shape.contains(x, y);
        }
        return super.contains(x, y);
    }

    Shape shape;

    public void paintComponent(Graphics g) {

        /*
         * Initial try at getting each component to paint he annotation content.
         * Not quite working but though it should be kept for future work.
         */

        // sniff out tool bar state to set correct annotation border
//        isEditable = (
//                (documentViewModel.getViewToolMode() == DocumentViewModel.DISPLAY_TOOL_SELECTION)
//                        &&
//                        !(annotation.getFlagReadOnly() || annotation.getFlagLocked() ||
//                                annotation.getFlagInvisible() || annotation.getFlagHidden()));


//        Graphics2D gg2 = (Graphics2D) g;
//        AffineTransform oldTransform = gg2.getTransform();
//
//        Rectangle2D bounds = annotation.getUserSpaceRectangle();// getBounds();
        // apply page transform.
//        AffineTransform at = currentPage.getToPageSpaceTransform(
//                documentViewModel.getPageBoundary(),
//                documentViewModel.getViewRotation(),
//                documentViewModel.getViewZoom());
//        PRectangle pageBoundary = currentPage.getPageBoundary(documentViewModel.getPageBoundary());
//        at.translate(pageBoundary.getX() -bounds.getX(),
//                pageBoundary.getY() -  bounds.getY() - bounds.getHeight());
//        gg2.transform(at);

        // get current tool state, we don't want to draw the highlight
        // state if the selection tool is selected.
//        boolean notSelectTool =
//                documentViewModel.getViewToolMode() !=
//                        DocumentViewModel.DISPLAY_TOOL_SELECTION;

        // paint all annotations on top of the content buffer
//        annotation.render(gg2,
//                GraphicsRenderingHints.SCREEN,
//                documentViewModel.getViewRotation(),
//                documentViewModel.getViewZoom(),
//                hasFocus() && notSelectTool);

        // reset the graphics context so that the border will show up in the
        // correct spot.
//        gg2.setTransform(oldTransform);


    }
}
