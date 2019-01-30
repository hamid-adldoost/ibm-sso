package com.ibm.sso.common;

// Generated By AEF3 Framework, powered by Dr.Adldoost :D
import com.google.common.base.Throwables;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class AEFExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<RestErrorMessage> handleRestException(RuntimeException ex, WebRequest request) {

        String errorMessage = "";
        Integer code = -1;

        if(ex instanceof SecurityServiceException) {
            errorMessage = ErrorCodeReaderUtil.getResourceProperity("UNAUTHORIZED");
            code = 0;
            return new ResponseEntity<>(new RestErrorMessage(errorMessage, code), HttpStatus.UNAUTHORIZED);
        }

        try {
            errorMessage = ErrorCodeReaderUtil.getResourceProperity(ex.getMessage());
            BusinessExceptionCode bec = BusinessExceptionCode.findByName(ex.getMessage());
            if(bec != null)
                code = bec.getValue();
            else
                code = 0;
            return new ResponseEntity<>(new RestErrorMessage(errorMessage, code), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
//            errorMessage = ErrorCodeReaderUtil.getResourceProperity(BusinessExceptionCode.GENERAL_ERROR.name());

            errorMessage = ex.getMessage();
            if(errorMessage == null)
                errorMessage = Throwables.getStackTraceAsString ( ex ) ;
            code = -1;
        }

        return new ResponseEntity<>(new RestErrorMessage(errorMessage, code), HttpStatus.BAD_REQUEST);

    }
}
