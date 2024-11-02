#!/bin/sh

set -x

#----------
# Bash script for 
# - downloading wildfly
# - run, shutdown, status wildfly
# - run jboss-cli
# - deploying exploded app
# - undeploying app
# - add/remove skipdeploy app
#
BASEDIR=$(dirname "$0")
FULL_PATH_TO_SCRIPT="$(realpath "${BASEDIR}")"
BASEDIR=${FULL_PATH_TO_SCRIPT}
JBOSS_BASEDIR=${BASEDIR}/target/wildfly/wildfly-25.0.0.Final
JBOSS_BINDIR=${JBOSS_BASEDIR}/bin
JBOSS_DEPLOYMENTSDIR=${JBOSS_BASEDIR}/standalone/deployments

DEPLOYMENT_ARTIFACT=plantumlbootstrap-1.0-SNAPSHOT

CMD_MKLINK_LINK=${BASEDIR}/target/${DEPLOYMENT_ARTIFACT}
CMD_MKLINK_TARGET=${JBOSS_DEPLOYMENTSDIR}/${DEPLOYMENT_ARTIFACT}.war

#----------
#
usage () {
  cat << -EOF-
Usage $0 : [ commands ]

general commands:
  help

wildfly commands:
  install_wildfly
  run_wildfly : run wildfly
  status_wildfly : show status of wildfly
  shutdown_wildfly : shutdown running wildfly
  run_jboss_cli : launch jboss-cli

deployment commands: 
  create_mklink : create symbolik link for ${DEPLOYMENT_ARTIFACT}
  dodeploy : deploy application ${DEPLOYMENT_ARTIFACT}
  undeploy : undeploy application ${DEPLOYMENT_ARTIFACT}
  add_skipdeploy : mark application ${DEPLOYMENT_ARTIFACT} as skipable
  rm_skipdeploy : unmark application ${DEPLOYMENT_ARTIFACT} as skipable

deployment link: ${CMD_MKLINK_LINK}
deployment target: ${CMD_MKLINK_TARGET}

-EOF-
}

#----------
# wildfly commands:
install_wildfly () {
  $M2_HOME/bin/mvn -Pinstall-wildfly dependency:unpack@unpack
}

run_wildfly() {
  "${JBOSS_BINDIR}/standalone.sh" --debug -Djboss.socket.binding.port-offset=1
}

status_wildfly() {
  "${JBOSS_BINDIR}/jboss-cli.sh" --controller=localhost:9991 --connect --command=":read-attribute(name=server-state)"
}

shutdown_wildfly() {
  "${JBOSS_BINDIR}/jboss-cli.sh" --controller=localhost:9991 --connect --command=":shutdown"
}

run_jboss_cli() {
  ${JBOSS_BINDIR}/jboss-cli.sh --controller=localhost:9991 --connect
}

#----------
# deployment commands: 
create_mklink_cmd () {
  if [ ! -d "${CMD_MKLINK_TARGET}" ]
  then
    cmd << -EOF-
mklink /J "${CMD_MKLINK_TARGET//\//\\/}" "${CMD_MKLINK_LINK//\//\\/}"
-EOF-
  fi
}
create_mklink () {
  if [ ! -d "${CMD_MKLINK_TARGET}" ]
  then
    ln -s "${CMD_MKLINK_LINK}" "${CMD_MKLINK_TARGET}"
  fi
}

dodeploy () {
  touch ${CMD_MKLINK_TARGET}.dodeploy
}

undeploy () {
  rm ${CMD_MKLINK_TARGET}.deployed
}

add_skipdeploy () {
  touch ${CMD_MKLINK_TARGET}.skipdeploy
}
rm_skipdeploy () {
  rm ${CMD_MKLINK_TARGET}.skipdeploy
}


#----------
#
case "$1" in
    help)
      usage
      ;;
    install_wildfly)
      install_wildfly
      ;;
    run_wildfly)
      run_wildfly
      ;;
    status_wildfly)
      status_wildfly
      ;;
    shutdown_wildfly)
      shutdown_wildfly
      ;;
    run_jboss_cli)
      run_jboss_cli
      ;;
    create_mklink)
      create_mklink
      ;;
    dodeploy)
      dodeploy
      ;;
    undeploy)
      undeploy
      ;;
    add_skipdeploy)
      add_skipdeploy
      ;;
    rm_skipdeploy)
      rm_skipdeploy
      ;;
    *)
      usage
      exit 2
      ;;
esac

