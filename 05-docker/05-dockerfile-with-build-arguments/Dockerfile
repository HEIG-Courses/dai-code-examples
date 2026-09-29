# Base image
FROM debian:13

# Arguments
ARG TIMEZONE="Europe/Zurich"

# Set the timezone in its different configuration files
RUN ln -sf /usr/share/zoneinfo/${TIMEZONE} /etc/localtime && echo "${TIMEZONE}" > /etc/timezone

# Command to run
CMD ["date"]
