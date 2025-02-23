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
package org.icepdf.ri.common;

import java.awt.*;

/**
 * Base model for colour picker models.  should probably have extended default button model.
 *
 * @since 6.3
 */
public interface PaintButtonInterface {

    void setColor(Color color);

    void setColorBound(Shape colorBound);

    void setAlpha(float alpha);

    Color getColor();

    Shape getColorBound();

    float getAlpha();
}
