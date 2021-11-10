#!/bin/sh

#set -x

#----------
#
BASEDIR=$(dirname "$0")
JBOSS_BASEDIR=${BASEDIR}/target/wildfly/wildfly-25.0.0.Final
JBOSS_BINDIR=${JBOSS_BASEDIR}/bin
JBOSS_DEPLOYMENTSDIR=${JBOSS_BASEDIR}/standalone/deployments
DEPLOYMENT_ARTIFACT=plantumlbootstrap-1.0-SNAPSHOT

CMD_MKLINK_LINK=${BASEDIR}/target/${DEPLOYMENT_ARTIFACT}
CMD_MKLINK_TARGET=${JBOSS_DEPLOYMENTSDIR}/${DEPLOYMENT_ARTIFACT}.war

#----------
#

usage () {
  echo "Usage $0 : [ install_wildfly | run_wildfly | status_wildfly | shutdown_wildfly | run_jboss_cli | create_mklink | dodeploy | undeploy | skipdeploy ]"


  echo "deployment link: ${CMD_MKLINK_LINK}"
  echo "deployment target: ${CMD_MKLINK_TARGET}"
}

install_wildfly () {
  $M2_HOME/bin/mvn -Pinstall-wildfly dependency:unpack@unpack
}

run_wildfly() {
  ${JBOSS_BINDIR}/standalone.sh --debug -Djboss.socket.binding.port-offset=1
}

status_wildfly() {
  ${JBOSS_BINDIR}/jboss-cli.sh --controller=localhost:9991 --connect --command=":read-attribute(name=server-state)"
}

shutdown_wildfly() {
  ${JBOSS_BINDIR}/jboss-cli.sh --controller=localhost:9991 --connect --command=":shutdown"
}

run_jboss_cli() {
  ${JBOSS_BINDIR}/jboss-cli.sh --controller=localhost:9991 --connect
}

create_mklink () {
  if [ ! -d "${CMD_MKLINK_TARGET}" ]
  then
    cmd << -EOF-
mklink /J "${CMD_MKLINK_TARGET//\//\\/}" "${CMD_MKLINK_LINK//\//\\/}"
-EOF-
  fi
}

dodeploy () {
  touch ${CMD_MKLINK_TARGET}.dodeploy
}

undeploy () {
  rm ${CMD_MKLINK_TARGET}.deployed
}

skipdeploy () {
  touch ${CMD_MKLINK_TARGET}.skipdeploy
}


#----------
#
case "$1" in
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
    skipdeploy)
      skipdeploy
      ;;
    *)
      usage
      exit 2
      ;;
esac

