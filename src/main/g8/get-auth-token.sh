#!/usr/bin/env bash

export LOC=http://localhost:9000/api
echo "Register a Company and create the admin user" > auth.log
curl --silent --show-error -H "Content-Type: application/json" -X POST -d '{"company": "Digital Cat", "firstName": "Damir","lastName": "Palinic","email": "damir@palinic.com","username": "dpalinic","password": "test12345"}' "\$LOC/client/registration" >> auth.log
printf "\n" >> auth.log
sleep 12
echo "Login as the admin user" >> auth.log
export LO=`curl --silent --show-error -H "Content-Type: application/json" -X POST -d '{"username": "dpalinic","password": "test12345"}' "\$LOC/user/login"` >> auth.log
printf "\n" >> auth.log
export AT=`echo \${LO} | jq '.authToken' | sed -e 's/"//g'`
#AT=\${AT%\$'\r'}
export RT=`echo \${LO} | jq '.refreshToken' | sed -e 's/"//g'`
echo "Get the identity status" >> auth.log
sleep 1
curl --silent --show-error --header "Authorization: Bearer \${AT}" \${LOC}/state/identity >> auth.log
printf "\n" >> auth.log
echo "Create a second user" >> auth.log
sleep 1
curl --silent --show-error --header "Authorization: Bearer \${AT}" -X POST -d '{"firstName": "Jelena","lastName": "Palinic","email": "jelena@palinic.com","username": "jpalinic","password": "test12345"}' \${LOC}/user >> auth.log
printf "\n" >> auth.log
echo "Login as the second user" >> auth.log
sleep 12
export LO=`curl --silent --show-error -H "Content-Type: application/json" -X POST -d '{"username": "jpalinic","password": "test12345"}' "\$LOC/user/login"` >> auth.log
printf "\n" >> auth.log
export AT=`echo \${LO} | jq '.authToken' | sed -e 's/"//g'`
#AT=\${AT%\$'\r'}
export RT=`echo \${LO} | jq '.refreshToken' | sed -e 's/"//g'`
echo "Get the identity status" >> auth.log
sleep 1
curl --silent --show-error --header "Authorization: Bearer \${AT}" \${LOC}/state/identity >> auth.log
printf "\n" >> auth.log
echo \$AT