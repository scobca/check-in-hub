package org.scobca.checkinhub.exception

import javax.naming.AuthenticationException

/**
 * Exception thrown when JWT authentication fails.
 *
 * Maps to HTTP 401 Unauthorized status code.
 * Used for invalid tokens, expired tokens, missing tokens, or signature verification failures.
 * Extends Spring Security's [AuthenticationException] for proper security context handling.
 *
 * @param message Description of the authentication failure reason.
 *
 * @see AuthenticationException
 *
 * @author Vladimir Fokin
 * @since 1.0
 */
class JwtAuthenticationException(message: String) : AuthenticationException(message)