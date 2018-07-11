tell application "Terminal"
      activate

      set tab1 to do script
      set currentWindow to front window
      do script "cd  ~/Desktop/microservices/OrderService" in tab1
      do script "mvn clean install" in tab1

end tell
