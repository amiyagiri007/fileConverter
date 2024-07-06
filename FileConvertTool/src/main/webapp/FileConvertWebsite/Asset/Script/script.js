function getRandomBorderRadius() {
    const getRandomPercentage = () => Math.floor(Math.random() * 50) + 25; // Random percentage between 25 and 75
    return `${getRandomPercentage()}% ${getRandomPercentage()}% ${getRandomPercentage()}% ${getRandomPercentage()}% / ${getRandomPercentage()}% ${getRandomPercentage()}% ${getRandomPercentage()}% ${getRandomPercentage()}%`;
}

function getRandomColor() {
    const r = Math.floor(Math.random() * 256);
    const g = Math.floor(Math.random() * 256);
    const b = Math.floor(Math.random() * 256);
    const alpha = (Math.random() * 0.7).toFixed(2);
    const blurRadius = Math.floor(parseFloat(getRandomBorderRadius().split(' ')[0]) * 0.1); // Get the first radius value and calculate 10% of it for blur radius
    const blurValue = `blur(${blurRadius}px)`;

    const rgbaColor = `rgba(${r}, ${g}, ${b}, ${alpha})`;

    return { rgbaColor, blurValue };
}

function getRandomBorderColor() {
    // Generate random values for red, green, blue channels and alpha channel
    const r = Math.floor(Math.random() * 256); // Random value between 0 and 255 for red channel
    const g = Math.floor(Math.random() * 256); // Random value between 0 and 255 for green channel
    const b = Math.floor(Math.random() * 256); // Random value between 0 and 255 for blue channel
    const alpha = (Math.random() * 0.7).toFixed(1); // Random value between 0 and 0.7 for alpha channel with 1 decimal place

    // Construct the rgba color string
    return `rgba(${r}, ${g}, ${b}, ${alpha})`;
}

// Dynamic Shpae create with blur effect
function applyRandomStyle() {
    const shape = document.getElementById('shapeContainer');
    const { rgbaColor, blurValue } = getRandomColor();
    const borderRadius = getRandomBorderRadius();
    
    shape.style.background = rgbaColor;
    shape.style.borderRadius = borderRadius;
    shape.style.backdropFilter = blurValue;
    shape.style.webkitBackdropFilter = blurValue;

    // Calculate contrasting color for the border
    const rgbColor = rgbaColor.match(/\d+/g);
    const luminance = (0.299 * rgbColor[0] + 0.587 * rgbColor[1] + 0.114 * rgbColor[2]) / 255;
    const borderColor = getRandomBorderColor();
    
    shape.style.border = `3px  double  ${borderColor}`;
}

// progress bar hide reset btn clicked
function toggleButton() {
    const convertButton = document.getElementById('convertBtn');
    const resetButton = document.getElementById('resetBtn');
    const downloadButton = document.getElementById('downloadLink');
    const progressBar = document.getElementById('progressBarContainer');

    if (convertButton.style.display !== 'none') {
        convertButton.style.display = 'none';
        resetButton.style.display = 'inline-block';
        downloadButton.style.display = 'inline-block';
        progressBar.style.display = 'block';
    } else {
        convertButton.style.display = 'inline-block';
        resetButton.style.display = 'none';
        downloadButton.style.display = 'none';
        progressBar.style.display = 'none';
    }
}

document.getElementById('convertBtn').addEventListener('click', function() {
    toggleButton();
});

document.getElementById('resetBtn').addEventListener('click', function() {
    toggleButton();
});


setInterval(applyRandomStyle, 2000); // Change style every 2 seconds


//Convert and Reset  Button Animation
let animationId; // Store animation id to cancel animation

document.getElementById('convertBtn').addEventListener('click', function() {
    // Show progress container
    document.getElementById('progressContainer').classList.remove('hidden');
    // Hide convert button
    document.getElementById('convertBtn').classList.add('hidden');
    // Show reset button
    document.getElementById('resetBtn').classList.remove('hidden');
    
    // Simulate conversion process
    let progress = 0;
    const progressBar = document.getElementById('progressBar');
    const progressText = document.getElementById('progressText');
    
    const duration = 2000; // Duration of the animation in milliseconds
    const start = performance.now(); // Start time of the animation
    
    function animate(currentTime) {
        const elapsed = currentTime - start; // Time elapsed since the start of the animation
        const easedProgress = easeOut(elapsed, 0, 100, duration); // Calculate eased progress
        
        progressBar.style.width = easedProgress + '%';
        progressText.textContent = Math.round(easedProgress) + '%'; // Update progress text
        
        if (elapsed < duration) {
            animationId = requestAnimationFrame(animate); // Continue the animation
        } else {
            document.getElementById('progressContainer').classList.add('hidden');
            document.getElementById('downloadLink').classList.remove('hidden');
        }
    }
    
    animationId = requestAnimationFrame(animate);
});

document.getElementById('resetBtn').addEventListener('click', function() {
    // Cancel animation if it's running
    cancelAnimationFrame(animationId);
    
    // Reset form elements
    document.getElementById('fileUpload').value = '';
    document.getElementById('urlInput').value = '';
    document.getElementById('convertType').selectedIndex = 0;
    
    // Hide progress container, download link, and reset button
    document.getElementById('progressContainer').classList.add('hidden');
    document.getElementById('downloadLink').classList.add('hidden');
    document.getElementById('resetBtn').classList.add('hidden');
    
    // Show convert button
    document.getElementById('convertBtn').classList.remove('hidden');
});

// Ease-out function
function easeOut(t, b, c, d) {
    return c * (1 - Math.pow(1 - t / d, 3)) + b;
}

//hide error text

function hideText() {
            var element = document.getElementById("errorText");
            if (element) {
                setTimeout(function() {
                    element.style.display = "none";
                }, 7000); // 7000 milliseconds = 7 seconds
            }
        }
//var button = document.getElementById("convertBtn");

// Add click event listener to the button
//button.addEventListener('click', hideText);

window.onload = hideText;