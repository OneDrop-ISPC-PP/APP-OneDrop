const { app, BrowserWindow } = require('electron');

function createWindow () {
  const win = new BrowserWindow({
    width: 800,
    height: 600,
    webPreferences: {
      nodeIntegration: true,
    }
  });

  win.loadURL('http://localhost:4200'); // Asegúrate de que tu servidor Angular esté corriendo
}

app.whenReady().then(createWindow);