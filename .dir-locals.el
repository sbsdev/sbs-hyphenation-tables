;;; Directory Local Variables
;;; See Info node `(emacs) Directory Variables' for more information.

((nil
  . ((compile-command . (format "cd %s && mvn clean package"
                                (locate-dominating-file buffer-file-name "pom.xml"))))))
