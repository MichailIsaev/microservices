tell application "Terminal"
      activate

      set tab1 to do script
      set currentWindow to front window
      do script "cd  ~/Desktop/microservices/OrderService/VisitorsService" in tab1
      do script "mvn exec:java" in tab1


      set tab2 to do script
      do script "cd  ~/Desktop/microservices/OrderService/PricingService" in tab2
      do script "mvn exec:java" in tab2

      set tab3 to do script
      do script "cd  ~/Desktop/microservices/OrderService/BookingService" in tab3
      do script "mvn exec:java" in tab3

end tell
