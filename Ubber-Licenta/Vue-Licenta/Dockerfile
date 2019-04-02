FROM nginx:1.12.1-alpine

COPY ./dist /usr/share/nginx/html
COPY ./deploy/Nginx/default.conf /etc/nginx/conf.d/default.conf

EXPOSE 8080
