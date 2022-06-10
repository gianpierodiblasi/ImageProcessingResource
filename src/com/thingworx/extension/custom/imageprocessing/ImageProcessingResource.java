package com.thingworx.extension.custom.imageprocessing;

import com.thingworx.logging.LogUtilities;
import com.thingworx.metadata.annotations.ThingworxServiceDefinition;
import com.thingworx.metadata.annotations.ThingworxServiceParameter;
import com.thingworx.metadata.annotations.ThingworxServiceResult;
import com.thingworx.resources.Resource;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import org.slf4j.Logger;

public class ImageProcessingResource extends Resource {

  private final static Logger SCRIPT_LOGGER = LogUtilities.getInstance().getScriptLogger(ImageProcessingResource.class);
  private static final long serialVersionUID = 1L;

  @ThingworxServiceDefinition(name = "resize", description = "", category = "", isAllowOverride = false, aspects = {"isAsync:false"})
  @ThingworxServiceResult(name = "result", description = "", baseType = "IMAGE", aspects = {})
  public byte[] resize(
          @ThingworxServiceParameter(name = "image", description = "", baseType = "IMAGE") byte[] image,
          @ThingworxServiceParameter(name = "width", description = "", baseType = "INTEGER") Integer width,
          @ThingworxServiceParameter(name = "height", description = "", baseType = "INTEGER") Integer height) throws Exception {
    SCRIPT_LOGGER.debug("ImageProcessingResource - resize -> Start");

    ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(image));
    String format = ImageIO.getImageReaders(iis).next().getFormatName();
    BufferedImage bufferedImage = ImageIO.read(iis);

    if (width != null && width > 0 && height != null && height > 0) {
    } else if (width != null && width > 0) {
      height = bufferedImage.getHeight() * width / bufferedImage.getWidth();
    } else if (height != null && height > 0) {
      width = bufferedImage.getWidth() * height / bufferedImage.getHeight();
    } else {
      return image;
    }
    SCRIPT_LOGGER.debug("ImageProcessingResource - resize -> width = {}, height = {}", width, height);

    AffineTransform at = AffineTransform.getScaleInstance(width / (double) bufferedImage.getWidth(), height / (double) bufferedImage.getHeight());
    BufferedImage scaledImage = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC).filter(bufferedImage, null);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(scaledImage, format, baos);

    SCRIPT_LOGGER.debug("ImageProcessingResource - resize -> Stop");
    return baos.toByteArray();
  }

  @ThingworxServiceDefinition(name = "scale", description = "", category = "", isAllowOverride = false, aspects = {"isAsync:false"})
  @ThingworxServiceResult(name = "result", description = "", baseType = "IMAGE", aspects = {})
  @SuppressWarnings("SuspiciousNameCombination")
  public byte[] scale(
          @ThingworxServiceParameter(name = "image", description = "", baseType = "IMAGE") byte[] image,
          @ThingworxServiceParameter(name = "scaleX", description = "", baseType = "NUMBER") Double scaleX,
          @ThingworxServiceParameter(name = "scaleY", description = "", baseType = "NUMBER") Double scaleY) throws Exception {
    SCRIPT_LOGGER.debug("ImageProcessingResource - scale -> Start");

    ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(image));
    String format = ImageIO.getImageReaders(iis).next().getFormatName();
    BufferedImage bufferedImage = ImageIO.read(iis);

    if (scaleX != null && scaleX > 0 && scaleY != null && scaleY > 0) {
    } else if (scaleX != null && scaleX > 0) {
      scaleY = scaleX;
    } else if (scaleY != null && scaleY > 0) {
      scaleX = scaleY;
    } else {
      return image;
    }

    int width = (int) (bufferedImage.getWidth() * scaleX);
    int height = (int) (bufferedImage.getHeight() * scaleY);
    SCRIPT_LOGGER.debug("ImageProcessingResource - scale -> width = {}, height = {}", width, height);

    AffineTransform at = AffineTransform.getScaleInstance(scaleX, scaleY);
    BufferedImage scaledImage = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC).filter(bufferedImage, null);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(scaledImage, format, baos);

    SCRIPT_LOGGER.debug("ImageProcessingResource - scale -> Stop");
    return baos.toByteArray();
  }
}
