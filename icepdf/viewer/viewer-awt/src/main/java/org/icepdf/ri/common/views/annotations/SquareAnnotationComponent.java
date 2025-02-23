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

import org.icepdf.core.pobjects.annotations.SquareAnnotation;
import org.icepdf.ri.common.utility.annotation.properties.SquareAnnotationPanel;
import org.icepdf.ri.common.views.AbstractPageViewComponent;
import org.icepdf.ri.common.views.DocumentViewController;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * The SquareAnnotationComponent encapsulates a SquareAnnotation objects.  It
 * also provides basic editing functionality such as resizing, moving and change
 * the border color and style as well as the fill color.
 * <br>
 * The Viewer RI implementation contains a SquareAnnotationPanel class which
 * can edit the various properties of this component.
 *
 * @see SquareAnnotationPanel
 * @since 5.0
 */
@SuppressWarnings("serial")
public class SquareAnnotationComponent extends MarkupAnnotationComponent<SquareAnnotation> {


    public SquareAnnotationComponent(SquareAnnotation annotation, DocumentViewController documentViewController,
                                     AbstractPageViewComponent pageViewComponent) {
        super(annotation, documentViewController, pageViewComponent);
        isRollover = false;
        isShowInvisibleBorder = false;
    }

    @Override
    public void resetAppearanceShapes() {
        super.resetAppearanceShapes();
        refreshAnnotationRect();
        annotation.resetAppearanceStream(getToPageSpaceTransform());
    }

    @Override
    public void paintComponent(Graphics g) {

    }

    @Override
    public void mouseDragged(MouseEvent me) {
        super.mouseDragged(me);
        resetAppearanceShapes();
    }
}
