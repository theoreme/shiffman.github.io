// Graphing 2D Equations
// Daniel Shiffman <http://www.shiffman.net>

// Graphs the following equation
// sin(n*cos(r) + 5*theta)
// where n is a function of horizontal mouse location 

void setup() {
  size(200,200);
}

void draw() {
  loadPixels();
  float n = (mouseX * 10.0f) / width;
  float w = 16.0f;         // 2D space width
  float h = 16.0f;         // 2D space height
  float dx = w / width;    // Increment x this amount per pixel
  float dy = h / height;   // Increment y this amount per pixel
  float x = -w/2;          // Start x at -1 * width / 2
  for (int i = 0; i < width; i++) {
    float y = -h/2;        // Start y at -1 * height / 2
    for (int j = 0; j < height; j++) {
      float r = sqrt((x*x) + (y*y));    // Convert cartesian to polar
      float theta = atan2(y,x);         // Convert cartesian to polar
      // Compute 2D polar coordinate function
      float val = sin(n*cos(r) + 5 * theta);           // Results in a value between -1 and 1
      //float val = cos(r);                            // Another simple function
      //float val = sin(theta);                        // Another simple function
      // Map resulting vale to grayscale value
      pixels[i+j*width] = color((val + 1.0f)* 255.0/2.0);     // Scale to between 0 and 255
      y += dy;                // Increment y
    }
    x += dx;                  // Increment x
  }
  updatePixels();
}

