var ready = (callback) => {
  if (document.readyState != "loading") callback();
  else document.addEventListener("DOMContentLoaded", callback);
};

ready(() => {
  var copyAesKeyToClipboard = document.getElementById("copyAesKeyToClipboard");
  copyAesKeyToClipboard.addEventListener("click", function () {
    var copyText = document.getElementById("aesKey").innerText;
    navigator.clipboard.writeText(copyText).then(
      function () {
        console.log("Copied to clipboard");
        var copyIcon = document.getElementById("copyIcon");
        copyIcon.outerHTML = `
          <svg id="copyIcon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-check" viewBox="0 0 16 16">
            <path fill-rule="evenodd" d="M10.97 4.97a.75.75 0 0 1 1.07 1.05l-4.5 4.5a.75.75 0 0 1-1.08 0l-2-2a.75.75 0 1 1 1.08-1.05l1.47 1.47 3.97-3.97z"/>
          </svg>
        `;
      },
      function (err) {
        console.error("Could not copy text: ", err);
      }
    );
  });
});
