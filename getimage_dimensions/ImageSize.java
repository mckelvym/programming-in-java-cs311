synchronized Dimension getImageDimensions(Image im)
        throws ImageNotFoundException{
        // Get the width of the image.
        int width;
        int height;

        while((width=im.getWidth(this))< 0){
        try{
        wait();
        }catch(InterruptedException e){}
        if(imageLoadError){
        throw new ImageNotFoundException(im.getSource());
        }
        }

        // Get the height of the image.
        while((height=im.getHeight(this))< 0){
        try{
        wait();
        }catch(InterruptedException e){}
        if(imageLoadError){
        throw new ImageNotFoundException(im.getSource());
        }
        }

        return new Dimension(width,height);
        }
