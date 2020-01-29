keytool -genkey -alias fa -keyalg RSA -keysize 2048 -validity 1825 -keystore "sandeep.jks" -storetype JKS -dname "CN=sandeep,OU=BCCore,O=Organization,L=Pune,ST=Maharashtra,C=IN" -ext san=dns:fa.com,dns:FA.COM -keypass nopass -storepass nopass

keytool -genkey -alias fa -keyalg RSA -keysize 2048 -validity 1825 -keystore "gaurav.jks" -storetype JKS -dname "CN=gaurav,OU=BCCore,O=Organization,L=Pune,ST=Maharashtra,C=IN" -ext san=dns:fa.com,dns:FA.COM -keypass nopass -storepass nopass

keytool -genkey -alias fa -keyalg RSA -keysize 2048 -validity 1825 -keystore "bhaskar.jks" -storetype JKS -dname "CN=bhaskar,OU=BCCore,O=Organization,L=Pune,ST=Maharashtra,C=IN" -ext san=dns:fa.com,dns:FA.COM -keypass nopass -storepass nopass

keytool -genkey -alias fa -keyalg RSA -keysize 2048 -validity 1825 -keystore "swapnil.jks" -storetype JKS -dname "CN=swapnil,OU=BCCore,O=Organization,L=Pune,ST=Maharashtra,C=IN" -ext san=dns:fa.com,dns:FA.COM -keypass nopass -storepass nopass