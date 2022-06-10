# ImageProcessingResource
An extension to manage images.

**This Extension is provided as-is and without warranty or support. It is not part of the PTC product suite and there is no PTC support.**

## Description
This extension adds a Resource object providing utility services for image processing.

## Services
- *resize*: resizes an image (if both width and height are not defined then no resizing is performed)
  - input
    - image: the image - IMAGE (No default value)
    - width: the new width, none (or a number less or equal to zero) to compute width relative to height - INTEGER (No default value)
    - height: the new height, none (or a number less or equal to zero) to compute height relative to width - INTEGER (No default value)
  - output: IMAGE
- *scale*: scales an image (if both scaleX and scaleY are not defined then no scaling is performed)
  - input
    - image: the image - IMAGE (No default value)
    - scaleX: the X scale, none (or a number less or equal to zero) to make scaleX = scaleY - NUMBER (No default value)
    - scaleY: the Y scale, none (or a number less or equal to zero) to make scaleY = scaleX (No default value)
  - output: IMAGE

## Donate
If you would like to support the development of this and/or other extensions, consider making a [donation](https://www.paypal.com/donate/?business=HCDX9BAEYDF4C&no_recurring=0&currency_code=EUR).