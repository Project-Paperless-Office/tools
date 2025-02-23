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
package org.icepdf.core.pobjects.graphics.commands;

import org.icepdf.core.pobjects.Page;
import org.icepdf.core.pobjects.graphics.OptionalContentState;
import org.icepdf.core.pobjects.graphics.PaintTimer;
import org.icepdf.core.pobjects.graphics.Shapes;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * The ShapesDrawCmd stores a Shapes object, usually the result of a processing
 * a xObjects content stream.  When executed the stores Shapes object is
 * painted using the specified graphics context.
 *
 * @since 5.0
 */
public class ShapesDrawCmd extends AbstractDrawCmd {

    private Shapes shapes;

    public ShapesDrawCmd(Shapes shapes) {
        this.shapes = shapes;
    }

    public Shapes getShapes() {
        return shapes;
    }

    @Override
    public Shape paintOperand(Graphics2D g, Page parentPage, Shape currentShape,
                              Shape clip, AffineTransform base,
                              OptionalContentState optionalContentState,
                              boolean paintAlpha, PaintTimer paintTimer) throws InterruptedException {
        if (optionalContentState.isVisible() &&
                shapes != null) {
            shapes.setPageParent(parentPage);
            shapes.setPaintAlpha(paintAlpha);
            shapes.paint(g);
            shapes.setPageParent(null);
        }
        return currentShape;
    }
}
