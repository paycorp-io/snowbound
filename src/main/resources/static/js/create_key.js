var ready = (callback) => {
  if (document.readyState != "loading") callback();
  else document.addEventListener("DOMContentLoaded", callback);
};

function copyToClipboard(elementId, iconId) {
  var copyText = document.getElementById(elementId).innerText;
  navigator.clipboard.writeText(copyText).then(
    function () {
      var copyIcon = document.getElementById(iconId);
      copyIcon.outerHTML = `
        <svg id="${iconId}" xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-check" viewBox="0 0 16 16">
          <path fill-rule="evenodd" d="M10.97 4.97a.75.75 0 0 1 1.07 1.05l-4.5 4.5a.75.75 0 0 1-1.08 0l-2-2a.75.75 0 1 1 1.08-1.05l1.47 1.47 3.97-3.97z"/>
        </svg>
      `;
    },
    function (err) {
      console.error("Could not copy text: ", err);
    }
  );
}

ready(() => {
  var copyAesKeyToClipboard = document.getElementById("copyAesKeyToClipboard");
  var copyApiKeyToClipboard = document.getElementById("copyApiKeyToClipboard");

  copyAesKeyToClipboard.addEventListener("click", function () {
    copyToClipboard("aesKey", "copyIconAes");
  });

  copyApiKeyToClipboard.addEventListener("click", function () {
    copyToClipboard("apiKey", "copyIconApi");
  });
});